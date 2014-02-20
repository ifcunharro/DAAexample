function listState(done, fail, always) {
	done = typeof done !== 'undefined' ? done : function() {};
	fail = typeof fail !== 'undefined' ? fail : function() {};
	always = typeof always !== 'undefined' ? always : function() {};
	
	$.ajax({
		url: 'rest/state',
		type: 'GET'
	})
	.done(done)
	.fail(fail)
	.always(always);
}

function addAddress(address, done, fail, always) {
	done = typeof done !== 'undefined' ? done : function() {};
	fail = typeof fail !== 'undefined' ? fail : function() {};
	always = typeof always !== 'undefined' ? always : function() {};
	
	$.ajax({
		url: 'rest/state',
		type: 'POST',
		data: address
	})
	.done(done)
	.fail(fail)
	.always(always);
}

function modifyAddress(address, done, fail, always) {
	done = typeof done !== 'undefined' ? done : function() {};
	fail = typeof fail !== 'undefined' ? fail : function() {};
	always = typeof always !== 'undefined' ? always : function() {};
	
	$.ajax({
		url: 'rest/state/' + address.id,
		type: 'PUT',
		data: address
	})
	.done(done)
	.fail(fail)
	.always(always);
}

function deleteAddress(id, done, fail, always) {
	done = typeof done !== 'undefined' ? done : function() {};
	fail = typeof fail !== 'undefined' ? fail : function() {};
	always = typeof always !== 'undefined' ? always : function() {};
	
	$.ajax({
		url: 'rest/state/' + id,
		type: 'DELETE',
	})
	.done(done)
	.fail(fail)
	.always(always);
}