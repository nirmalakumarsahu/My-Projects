loginForm.on('submit', function(event) {
	var emailOrPhone = $('#emailOrPhone').val();
	var password = $('#password').val();

	var flag = true;
	if (emailOrPhone == '') {
		flag = false;
	}
	
	if (password == '') {
		flag = false;
	}
	
	if (flag) {
		
	}

	if (!flag) {
		event.preventDefault();
	}
});