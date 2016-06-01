(function() {
    'use strict';
    angular
        .module('vivaxDataManagerApp')

        .factory('LocationHttp', function ($http) {
            return {
                getDistinctCountries: function(){
                    return $http.get("api/location/countries");
                },
                getDistinctLocations: function(){
                    return $http.get("api/location/locations");
                },
                getLocationIdByCountryName: function(country){
                    return $http.get("api/location/locationIdByCountryName/"+country);
                }
            }
        })
        .factory('Location', Location);

    Location.$inject = ['$resource'];

    function Location ($resource) {
        var resourceUrl =  'api/locations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
