

var fun1 = function(name) {
	
//	var name = "asdf sdf sdfs  sdfsdfsdf adf dfsfsd adsf "
	var words = [];
	words = name.split(" ");
	var tab = ['red','blue','green'];
	for(var i=0; i<words.length; i++)
	{
		words[i] = '<font color='+tab[i%3]+'>'+words[i]+' </font>';
	}
	
	var result = "";
	for(var i=0; i<words.length; i++)
	{
		result+=words[i];
	}
	result = '<html>'+result+"</html>"
    return result;
}