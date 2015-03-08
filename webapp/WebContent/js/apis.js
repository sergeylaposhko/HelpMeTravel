hostname = "http://localhost:8080/aoeu/rest/";
imageHostName = "http://localhost:8080/aoeu/";

var cityGet = "city/";
var cityAll = "all?";
var cityWhere = "where?name="
var cityFromParam = "from=";
var cityToParam = "to=";
var cityByGuide = "byuser?userId=";
var cityAdd = "add";
cityByIdURL = hostname + cityGet + "byid?id="
cityByGuideURL = hostname + cityGet + cityByGuide;
cityAddURL = hostname + cityGet + cityAdd;
cityAllURL = hostname + cityGet + "all";

countryAllURL = hostname +  "country/all";

var placeGet = "place/";
var placeByCity = "bycity?cityId=";
var placeById = "byid?id=";
var placeByUser = "byuser?sessionId=";
var placeAdd = "add";
placesByCityURL = hostname + placeGet + placeByCity;
placeByIdUrl = hostname + placeGet + placeById;
placeByUserURL = hostname + placeGet + placeByUser;
placeAddURL = hostname + placeGet + placeAdd;

var questionGet = "question/";
var questionByCity = placeByCity;
var questionById = "byid?id=";
questionByIdURL = hostname + questionGet + questionById;
questionByCityURL = hostname + questionGet + questionByCity;
questionAddURL = hostname + questionGet + "add";

var answerGet = "answer/";
var answerAdd = "add/"
answerAddURL = hostname + answerGet + answerAdd;

var countryGet = "country/";
var countryById = "byid?id=";
countryByIdURL = hostname + countryGet + countryById;

var guideGet = "guide/";
var guideByCity = placeByCity;
var guideById = "byid?id="
var guideBySession = "bysession?sessionId=";
guideByCityURL = hostname + guideGet + guideByCity;
guideByIdURL = hostname + guideGet + guideById;
guideBySessionURL = hostname + guideGet + guideBySession;

userUrl = "user/";
userLoginUrl = hostname + userUrl + "login";
userRegisterUrl = hostname + userUrl + "register";
userLogoutUrl = hostname + userUrl + "logout";



var serverError = "Server is not available.";
commorErrorMessages = {
	0: serverError,
	1:serverError,
	500:serverError
}

postRequestTransformatter = function(obj) {
	var str = [];
	for (var p in obj)
		str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
	return str.join("&");
}