(function() {
    'use strict';
    angular
        .module('vivaxDataManagerApp')
        .factory('SiteDataViewDTO', function($resource) {
            //var data = [{name:'country', query:'China'}];
               //return $resource('api/siteData/searchByFilters');
               return $resource('api/siteData/searchByFilters', {}, {
            'query': { method: 'POST', isArray: true},
            'post': {
                method: 'POST',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
           'update': { method:'PUT' }
        });
    }
)})();
