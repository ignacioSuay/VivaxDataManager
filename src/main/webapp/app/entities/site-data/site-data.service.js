(function() {
    'use strict';
    angular
        .module('vivaxDataManagerApp')
        .factory('SiteData', SiteData);

    SiteData.$inject = ['$resource'];

    function SiteData ($resource) {
        var resourceUrl =  'api/site-data/:id';

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

