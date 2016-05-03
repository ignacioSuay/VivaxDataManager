/**
 * Created by steven on 03/05/16.
 */
(function() {
    'use strict';
    angular
        .module('vivaxDataManagerApp')
        .factory('Publication', function($resource) {
            return $resource('api/publication/searchByPubMedId/:pubMedId', {
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
