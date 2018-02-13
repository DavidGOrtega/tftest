package com.neocolorapp.tensorflow;

import java.util.Arrays;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


import android.util.Base64;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class TFBridgeCordova extends CordovaPlugin 
{
    private TensorFlowInferenceInterface tfii;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException 
    {
        if ( action.equals("load") ) 
        {
            try
            {
                this.load( args.getString(0) );
                callbackContext.success("Model loaded successfully");
                
            }catch(Exception e)
            {
                callbackContext.error( e.getMessage() );
            }
            
            return true;
        
        }else if ( action.equals("stylize") ) 
        {
            float[] img_data = null;
            float[] styles = null;

            try
            {
                JSONArray image_arr = args.getJSONArray(0);
                int img_width       = args.getInt(1);
                int img_height      = args.getInt(2);
                img_data            = new float[ image_arr.length() ]; 
                for (int i = 0; i < image_arr.length(); i++)
                    img_data[i] = (float) image_arr.getDouble(i);

                JSONArray style_arr = args.getJSONArray(3);
                styles      = new float[ style_arr.length() ];   
                for (int i = 0; i < style_arr.length(); i++)
                    styles[i] = (float) style_arr.getDouble(i);
                    
                String[] logs       = null;
                
                this.stylize(img_data, img_width, img_height, styles, logs);
                
                JSONObject output   = new JSONObject();
                output.put("styles", Arrays.toString(styles) );
                output.put("result1", Arrays.toString(img_data) );
                output.put("logs", logs);
                
                callbackContext.success( output );
                
            }catch(Exception e)
            {
                JSONObject output   = new JSONObject();
                output.put("error", e.getMessage());
                output.put("img_data", img_data);
                output.put("styles", styles);
                
                callbackContext.error( output );
            }
            
            return true;
        
        }else if ( action.equals("stylize_64") ) 
        {       
            try
            {
                private static final int NUM_STYLES     = 26;
                private static final String INPUT_NODE  = "input";
                private static final String STYLE_NODE  = "style_num";
                private static final String OUTPUT_NODE = "transformer/expand/conv3/conv/Sigmoid";

                float[] styleVals   = new float[NUM_STYLES];
                for (int i = 0; i < NUM_STYLES; ++i) 
                {
                    styleVals[i] = 1.0f / NUM_STYLES;
                }

                byte[] bytes_in     = Base64.decode(args.getString(0), Base64.DEFAULT);
                Bitmap bitmap       = BitmapFactory.decodeByteArray(bytes_in, 0, bytes_in.length); 

                float[] floatValues = new float[ bitmap.getWidth() * bitmap.getHeight() ];
                int[] intValues     = new int[ bitmap.getWidth() * bitmap.getHeight() ];

                bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

                for (int i = 0; i < intValues.length; ++i) 
                {
                    final int val           = intValues[i];
                    floatValues[i * 3]      = ((val >> 16) & 0xFF) / 255.0f;
                    floatValues[i * 3 + 1]  = ((val >> 8) & 0xFF) / 255.0f;
                    floatValues[i * 3 + 2]  = (val & 0xFF) / 255.0f;
                }

                tfii.feed(INPUT_NODE, floatValues, 1, bitmap.getWidth(), bitmap.getHeight(), 3);
                tfii.feed(STYLE_NODE, styleVals, NUM_STYLES);
                tfii.run(new String[] {OUTPUT_NODE}, true);
                tfii.fetch(OUTPUT_NODE, floatValues);

                for (int i = 0; i < intValues.length; ++i) 
                {
                  intValues[i] =
                      0xFF000000
                          | (((int) (floatValues[i * 3] * 255)) << 16)
                          | (((int) (floatValues[i * 3 + 1] * 255)) << 8)
                          | ((int) (floatValues[i * 3 + 2] * 255));
                }

                bitmap.setPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray    = byteArrayOutputStream .toByteArray();
                String base64_out   = Base64.encodeToString(byteArray, Base64.DEFAULT);

                JSONObject output   = new JSONObject();
                output.put("out", base64_out);
                callbackContext.success( output );

            }catch(Exception e)
            {
                callbackContext.error( e.getMessage() );
            }

            return true;
        }
        
        return false;
    }
    
    public void load(String model)
    {
        tfii = new TensorFlowInferenceInterface( this.cordova.getActivity().getAssets(), model );
    }
    
    //https://arxiv.org/abs/1610.07629 A Learned Representation For Artistic Style
    public void stylize(float[] img_data, int img_width, int img_height, float[] styles, String[] logs)
    {
        this.stylize(img_data, img_width, img_height, styles, logs, "input", "style_num", "transformer/expand/conv3/conv/Sigmoid");
    }
    
    public void stylize(float[] img_data, int img_width, int img_height, float[] styles, String[] logs, String input_node, String style_node, String output_node)
    {
        tfii.feed( input_node, img_data, 1, img_width, img_height, 3 );
        tfii.feed( style_node, styles, styles.length );
        tfii.run( new String[]{output_node}, true );              // Execute the output node's dependency sub-graph.
        tfii.fetch(output_node, img_data);                        // Copy the data from TensorFlow back into our array.
        
        logs = tfii.getStatString().split("\n");
    }
}