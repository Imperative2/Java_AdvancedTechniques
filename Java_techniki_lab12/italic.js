

var fun1 = function(name) {
	
//	var name = "asdf sdf sdfs  sdfsdfsdf adf dfsfsd adsf "
	var words = [];
	words = name.split(" ");
//	var tab = ['red','blue','green'];
	for(var i=0; i<words.length; i++)
	{
		words[i] = ''+words[i]+' ';

	}
	
	var result = "";
	for(var i=0; i<words.length; i++)
	{
		result+=words[i];
	}
	result = '<html><i>'+result+"</i></html>"
    return result;
}