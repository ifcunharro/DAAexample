var stateFormId = 'state-form';
var stateListId = 'state-list';
var stateFormQuery = '#' + stateFormId;
var stateListQuery = '#' + stateListId;

function insertStateList(parent) {
	parent.append(
		'<table id="' + stateListId + '">\
			<tr>\
				<th>Calle</th>\
				<th>Número</th>\
				<th>Localidad</th>\
				<th>Provincia</th>\
				<th></th>\
				<th></th>\
			</tr>\
		</table>'
	);
}

function insertStateForm(parent) {
	parent.append(
		'<form id="' + stateFormId + '">\
			<input name="id" type="hidden" value=""/>\
			<input name="street" type="text" value="" />\
			<input name="number" type="text" value=""/>\
			<input name="locality" type="text" value=""/>\
			<input name="province" type="text" value=""/>\
			<input id="btnSubmit" type="submit" value="Create"/>\
			<input id="btnClear" type="reset" value="Limpiar"/>\
		</form>'
	);
}

function createAddressRow(address) {
	return '<tr id="address-'+ address.id +'">\
		<td class="street">' + address.street + '</td>\
		<td class="number"><right>' + address.number + '</right></td>\
		<td class="locality">' + address.locality + '</td>\
		<td class="province">' + address.province + '</td>\
		<td>\
			<a class="edit" href="#">Edit</a>\
		</td>\
		<td>\
			<a class="delete" href="#">Delete</a>\
		</td>\
	</tr>';
}

function formToAddress() {
	var form = $(stateFormQuery);
	return {
		'id': form.find('input[name="id"]').val(),
		'street': form.find('input[name="street"]').val(),
		'number': form.find('input[name="number"]').val(),
		'locality' : form.find('input[name="locality"]').val(),
		'province' : form.find('input[name="province"]').val()
	};
}

function addressToForm(address) {
	var form = $(stateFormQuery);
	form.find('input[name="id"]').val(address.id);
	form.find('input[name="street"]').val(address.street);
	form.find('input[name="number"]').val(address.number);
	form.find('input[name="locality"]').val(address.locality);
	form.find('input[name="province"]').val(address.province);
}

function rowToAddress(id) {
	var row = $('#address-' + id);

	return {
		'id': id,
		'street': row.find('td.street').text(),
		'number': row.find('td.number').text(),
		'locality': row.find('td.locality').text(),
		'province': row.find('td.province').text()
	};
}

function isEditing() {
	return $(stateFormQuery + ' input[name="id"]').val() != "";
}

function disableForm() {
	$(stateFormQuery + ' input').prop('disabled', true);
}

function enableForm() {
	$(stateFormQuery + ' input').prop('disabled', false);
}

function resetForm() {
	$(stateFormQuery)[0].reset();
	$(stateFormQuery + ' input[name="id"]').val('');
	$('#btnSubmit').val('Crear');
}

function showErrorMessage(jqxhr, textStatus, error) {
	alert(textStatus + ": " + error);
}

function addRowListeners(address) {
	$('#address-' + address.id + ' a.edit').click(function() {
		addressToForm(rowToAddress(address.id));
		$('input#btnSubmit').val('Modificar');
	});
	
	$('#address-' + address.id + ' a.delete').click(function() {
		if (confirm('EstÃ¡ a punto de eliminar una dirección. Â¿EstÃ¡ seguro de que desea continuar?')) {
			deleteAddress(address.id,
				function() {
					$('tr#address-' + address.id).remove();
				},
				showErrorMessage
			);
		}
	});
}

function appendToTable(address) {
	$(stateListQuery + ' > tbody:last')
		.append(createAddressRow(address));
	addRowListeners(address);
}

function initState() {
	$.getScript('js/dao/state.js', function() {
		listState(function(state) {
			$.each(state, function(key, address) {
				appendToTable(address);
			});
		});
		
		$(stateFormQuery).submit(function(event) {
			var address = formToAddress();
			
			if (isEditing()) {
				modifyAddress(address,
					function(address) {
						$('#address-' + address.id + ' td.street').text(address.street);
						$('#address-' + address.id + ' td.number').text(address.number);
						$('#address-' + address.id + ' td.locality').text(address.locality);
						$('#address-' + address.id + ' td.province').text(address.province);
						resetForm();
					},
					showErrorMessage,
					enableForm
				);
			} else {
				addAddress(address,
					function(address) {
						appendToTable(address);
						resetForm();
					},
					showErrorMessage,
					enableForm
				);
			}
			
			return false;
		});
		
		$('#btnClear').click(resetForm);
	});
};
