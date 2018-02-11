var TFBridgeCordova     = function(){};
TFBridgeCordova.prototype.load       = function(model, successCallback, errorCallback) 
{
    cordova.exec(successCallback, errorCallback, "TFBridgeCordova", "load", [model]);
};
TFBridgeCordova.prototype.stylize_load   = function(successCallback, errorCallback)
{
    this.load("file:///android_asset/stylize/stylize_quantized.pb", successCallback, errorCallback);
};
TFBridgeCordova.prototype.stylize    = function(img_data, img_width, img_height, styles, successCallback, errorCallback) 
{
    var opts = [ 
    img_data, img_width, img_height,    //[r, g, b, r, g, b...]
    styles, ];                          //[1, 0, ...];
    
    cordova.exec(successCallback, errorCallback, "TFBridgeCordova", "stylize", opts);
};