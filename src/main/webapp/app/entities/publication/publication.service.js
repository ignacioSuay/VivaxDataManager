(function() {
    'use strict';
    angular
        .module('vivaxDataManagerApp')
        .factory('Publication', Publication);

    Publication.$inject = ['$resource'];

    function Publication ($resource) {
        var resourceUrl =  'api/publications/:id';

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
