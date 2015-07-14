(function() {
	if (String.prototype.trim == undefined) {
		String.prototype.trim = function() {

			return this.replace(/\s+/, '').replace(/\s+$/, '');
		};
	}

	if (String.prototype.isEmpty == undefined) {
		String.prototype.isEmpty = function() {

			return this.trim().length < 1;
		};
	}

})();

var Try = {
	these : function() {
		var returnValue;

		for (var i = 0, length = arguments.length; i < length; i++) {
			var lambda = arguments[i];
			try {
				returnValue = lambda();
				break;
			} catch (e) {
			}
		}

		return returnValue;
	}
};

