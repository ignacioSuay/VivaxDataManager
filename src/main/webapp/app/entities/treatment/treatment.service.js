(function() {
    'use strict';
    angular
        .module('vivaxDataManagerApp')
        .factory('Treatment', Treatment);

    Treatment.$inject = ['$resource'];

    function Treatment ($resource) {
        var resourceUrl =  'api/treatments/:id';

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
