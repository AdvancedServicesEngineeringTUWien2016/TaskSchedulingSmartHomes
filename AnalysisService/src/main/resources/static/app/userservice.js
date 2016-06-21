angular.module('userService', [])
    .factory('userService', function($http, $q, $location, $route, $rootScope, $window) {

    var authenticate =  function(credentials){
        return $q(function(resolve, reject) {
            var headers = credentials ? {authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)} : {};

            $http
                .get('/current', {headers: headers})
                .success(function(data){resolve(data)})
                .error(function(error){reject(error)});
        });
    };

    var current_user = null;

    var service = {

        registerPath: "/register",
        loginPath: "/login",
        logoutPath: "/logout",
        homePath: "/",

        path: "",

        user : current_user,

        initRedirection : function(){
            $rootScope.$on('$routeChangeStart', function(event) {
                var newPath = $location.path();
                if (newPath != service.loginPath && newPath != service.registerPath) {
                    service.path = newPath;
                    if (!service.user) {
                        service.reload().then(
                            function(){
                                $location.path(newPath);
                                $route.reload();
                            }, function () {
                                $location.path(service.loginPath);
                            });
                        event.preventDefault();
                    }
                }
            });
        },

        reload : function(){
            return $q(function(resolve, reject) {
                service.login().then(function () {
                	$window.location.reload();
                    resolve(service.user);
                }, function (error) {
                    reject(error);
                })
            });
        },

        login : function(credentials){
            return $q(function(resolve, reject) {
                authenticate(credentials).then(function(data){
                    current_user = data;
                    service.user = data;
                    $window.location.reload();
                    $location.path(service.path == service.loginPath ? service.homePath : service.path);
                    resolve(data);
                }, function(error){
                    reject(error);
                });
            });
        },

        logout : function(){
            return $q(function(resolve, reject) {
                current_user = null;
                service.user = null;
                service.path = service.homePath;
                $http.post("/logout")
                    .success(function(){
                    	
                    	console.log("AD");
                    	$location.path('/login');
                        // get new csrf token
                        service.reload();
                        resolve();
                    })
                    .error(function(error){
                    	$window.location.reload();
                    	$location.path('/login');
                    	reject(error); 
                    });
            });
        }
    };

    return service;
});