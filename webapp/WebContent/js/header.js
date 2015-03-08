var loginDialog = 0;
var signUpDialog = 0;
var toasts = [];

session = 0;

function HeaderController($scope, $http, Data) {
	$scope.data = Data;
	$scope.loginPassword = "";
	$scope.loginLogin = "";
	$scope.error = {
		showLoginError : false
	}
	$scope.signup = {
		login : "",
		password : "",
		confirmPassword : "",
		firstName : "",
		lastName : ""
	}
	$scope.toggleloginDialog = function() {
		getLoginDialog().toggle();
	}
	$scope.toggleSignUpDialog = function() {
		getSignUpDialog().toggle();
	}
	$scope.loginAction = function() {
		getLoginDialog().toggle();
		var req = {
			url : userLoginUrl,
			method : "POST",
			data : {
				'login' : $scope.loginLogin,
				'password' : $scope.loginPassword
			},
			transformRequest : postRequestTransformatter,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}
		$http(req).success(function(data, status, headers, config) {
			getToast("ok-login-toast").show();
			$scope.data.loggined = true;
			session = data.sessionId;
			setCookie("sessionId", data.sessionId);
			Data.loggined = true;
			update();
			//window.document.location.reload();
		}).error(function(data, status, headers, config) {
			getToast('error-login-toast').show();
			Data.loggined = false;
			update();
		});
	}
	$scope.loginCancelAction = function() {
		getLoginDialog().toggle();
	}
	$scope.signupAction = function() {
		var isValid = validate();
		if (!isValid) {
			return;
		}
		getSignUpDialog().toggle();
		var formData = new FormData();
		formData.append("login", $scope.signup.login);
		formData.append("password", $scope.signup.password);
		formData.append("firstName", $scope.signup.firstName);
		formData.append("lastName", $scope.signup.lastName);
		var req = {
			url : userRegisterUrl,
			method : "POST",
			data : formData,
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}
		$http(req).success(function(data, status, headers, config) {
			getToast("ok-signup-toast").show();
		}).error(function(data, status, headers, config) {
			getToast('error-signup-toast').show();
			debugger;
		});
	}
	$scope.signupCancelAction = function() {
		getSignUpDialog().toggle();
	}
	$scope.logoutAction = function() {
		var req = {
			url : userLogoutUrl,
			method : "POST",
			data : {
				'sessionId' : session,
			},
			transformRequest : postRequestTransformatter,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}
		$http(req).success(function(data, status, headers, config) {
		}).error(function(data, status, headers, config) {
			console.log(data);
		});
		$scope.data.loggined = false;
		session = 0;
		setCookie("sessionId", "");
		Data.loggined = false;
		update();
		//window.document.location.reload();
	}
	
	var update = function() {
		if(Data.update){
			Data.update();
		}
	}

	var validate = function() {
		var email = $scope.signup.login;
		if (!isEmail(email))
			return false;
		var pass = $scope.signup.password;
		var confirmPass = $scope.signup.confirmPassword;
		if (pass != confirmPass)
			return false;
		if (!($scope.signup.firstName && $scope.signup.lastName))
			return false;
		return true;
	}
}

var getLoginDialog = function() {
	return loginDialog ? loginDialog : loginDialog = document
			.getElementById('loginDialog');
}
var getSignUpDialog = function() {
	return signUpDialog ? signUpDialog : signUpDialog = document
			.getElementById('signUpDialog');
}

var getToast = function(id) {
	return toasts[id] ? toasts[id] : toasts[id] = document.getElementById(id);
}

function setCookie(cname, cvalue, exdays) {
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ')
			c = c.substring(1);
		if (c.indexOf(name) != -1)
			return c.substring(name.length, c.length);
	}
	return "";
}

function isEmail(email) {
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}