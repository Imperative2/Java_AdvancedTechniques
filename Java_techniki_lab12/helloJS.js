var fun1 = function(name) {
    print('Hi there from Javascript, ' + name);
    return 'Hi there from Javascript, ' + name;
};

var fun2 = function (object) {
    print("JS Class Definition: " + Object.prototype.toString.call(object));
};