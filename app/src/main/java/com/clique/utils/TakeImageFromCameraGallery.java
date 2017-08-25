package com.clique.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.clique.R;


public class TakeImageFromCameraGallery {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY_IMAGE = 2;
    File photoFile = null;
    Uri photoURI;
    GetImage getImage;
    private boolean isCrop = false;

    private ImageLoadingUtils utils;
    private Context context;

    private Cursor cursor;
    private LruCache<String, Bitmap> memoryCache;

    public TakeImageFromCameraGallery(GetImage getImage, Context context) {
        this.context = context;
        this.getImage = getImage;
        utils = new ImageLoadingUtils(context);
        int cachesize = 60 * 1024 * 1024;
        memoryCache = new LruCache<String, Bitmap>(cachesize) {
            @SuppressLint("NewApi")
            @Override
            protected int sizeOf(String key, Bitmap value) {
                if (Build.VERSION.SDK_INT >= 12) {
                    return value.getByteCount();
                } else {
                    return value.getRowBytes() * value.getHeight();
                }
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    public interface GetImage {
        public void onGetTakeImagefromCamera(Uri uri, File file);
    }

    public void dispatchTakePictureIntent(Object object, boolean isCrop, int selectOptions) {
        this.isCrop = isCrop;
        if (selectOptions == 1) {
            galleryIntent(object);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                try {
                    photoFile = createImageFile(context, false);
                } catch (IOException ex) {

                }
                // Continue only if the File was successfully created ///storage/sdcard0/Android/data/com.hygienehealth/files/Pictures/Hygiene Health/IMG_20170320_233506.jpg
                photoURI = getUri(photoFile, context);
                List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(takePictureIntent, context.getPackageManager().MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    context.grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (object instanceof Activity) {
                    ((Activity) context).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } else if (object instanceof Fragment) {
                    ((Fragment) object).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } else if (object instanceof android.app.Fragment) {
                    ((android.app.Fragment) object).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }

        }
    }

    private void galleryIntent(Object object) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        if (object instanceof Activity) {
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY_IMAGE);
        } else if (object instanceof Fragment) {
            ((Fragment) object).startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY_IMAGE);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY_IMAGE);
        }
    }


    public void onActivityForResult(int requestCode, int resultCode, Intent data, Object object, Activity context) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == context.RESULT_OK) {
            if (isCrop) {
                beginCrop(photoURI, object);
            } else if (photoFile.length() > 1024000)
                new ImageCompressionAsyncTask(false).execute(photoFile.getAbsolutePath());
            else
                getImage.onGetTakeImagefromCamera(photoURI, photoFile);

        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data, context);
        } else if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == context.RESULT_OK) {
            if (isCrop && data != null) {
                beginCrop(data.getData(), object);
            } else {
                try {

                    InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                    if (inputStream != null) {
                        File file = getImageFile(context, true, inputStream);
                        if (file.length() > 1024000)
                            new ImageCompressionAsyncTask(true).execute(file.getAbsolutePath());
                        else
                            getImage.onGetTakeImagefromCamera(getUri(file,context), file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private void beginCrop(Uri source, Object object) {
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";

        photoFile = new File(context.getCacheDir(), fname);
        Uri destination = getUri(photoFile,context);
        if (object instanceof Activity) {
            Crop.of(source, destination).asSquare().start(((Activity) object));
        } else if (object instanceof Fragment) {
            Crop.of(source, destination).asSquare().start(context, ((Fragment) object));
        } else if (object instanceof android.app.Fragment) {
            Crop.of(source, destination).asSquare().start(context, ((android.app.Fragment) object));
        }
    }

    private void handleCrop(int resultCode, Intent result, Activity activity) {
        if (resultCode == activity.RESULT_OK) {
            getImage.onGetTakeImagefromCamera(Crop.getOutput(result), photoFile);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(activity, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile(Context context, boolean createTemp) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File maindirect = new File(storageDir, context.getString(R.string.app_name));
        if (!maindirect.exists())
            maindirect.mkdir();
        File image = new File(storageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        if (!image.exists() && !createTemp) {
            image.createNewFile();
        } else {
            image = File.createTempFile(
                    "IMG_" + timeStamp,  /* prefix */
                    ".jpg",         /* suffix */
                    maindirect     /* directory */
            );
        }
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private File getImageFile(Context context, boolean createTemp, InputStream inputStream) throws IOException {
        File file = createImageFile(context, createTemp);
        if (inputStream != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            copyStream(inputStream, fileOutputStream);
            fileOutputStream.close();
            inputStream.close();
        }
        return file;
    }

    public void copyStream(InputStream input, OutputStream output) throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }


    class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {
        private boolean fromGallery;

        public ImageCompressionAsyncTask(boolean fromGallery) {
            this.fromGallery = fromGallery;
        }

        @Override
        protected String doInBackground(String... params) {
            String filePath = compressImage(params[0]);
            return filePath;
        }

        public String compressImage(String imageUri) {

            //String filePath = getRealPathFromURI(imageUri);
            Bitmap scaledBitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(imageUri, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;

                }
            }

            options.inSampleSize = utils.calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                bmp = BitmapFactory.decodeFile(imageUri, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));


            ExifInterface exif;
            try {
                exif = new ExifInterface(imageUri);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + orientation);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream out = null;
            String filename = null;
            try {
                filename = createImageFile(context, true).getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out = new FileOutputStream(filename);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return filename;

        }


        public String getFilename() {
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
            if (!file.exists()) {
                file.mkdirs();
            }
            String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
            return uriSting;

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (fromGallery) {
                getImage.onGetTakeImagefromCamera(getUri(new File(result),context), new File(result));
               /* Bundle bundle = new Bundle();
                bundle.putString("FILE_PATH", result);
                showDialog(1, bundle);*/
            } else {
                getImage.onGetTakeImagefromCamera(getUri(new File(result),context), new File(result));
            }
        }

    }

    public static Uri getUri(File photoFile, Context context) {
        Uri photoURI = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(context,
                        "com.clique.fileprovider",
                        photoFile);
            }
        } else {
            photoURI = Uri.fromFile(photoFile);

        }
        return photoURI;
    }

}
