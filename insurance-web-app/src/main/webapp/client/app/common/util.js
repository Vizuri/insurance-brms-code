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

