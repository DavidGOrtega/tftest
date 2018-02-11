package com.neocolorapp.tensorflow;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

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
            try
            {
                JSONArray image_arr = args.getJSONArray(0);
                int img_width       = args.getInt(1);
                int img_height      = args.getInt(2);
                float[] img_data    = new float[ image_arr.length() ]; 
                for (int i = 0; i < image_arr.length(); i++)
                    img_data[i] = (float) image_arr.getDouble(i);
                
                JSONArray style_arr = args.getJSONArray(3);
                float[] styles      = new float[ style_arr.length() ];   
                for (int i = 0; i < style_arr.length(); i++)
                    styles[i] = (float) style_arr.getDouble(i);
                    
                String[] logs;
                
                this.stylize(img_data, img_width, img_height, styles, logs);
                
                JSONObject output   = new JSONObject();
                output.put("result", img_data);
                output.put("logs", logs);
                callbackContext.success(output);
                
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