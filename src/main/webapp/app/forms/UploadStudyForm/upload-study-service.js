/**
 * Created by steven on 03/05/16.
 */
(function() {
    'use strict';
    angular
        .module('vivaxDataManagerApp')
        .factory('PublicationService', function($resource) {
                alert('here i am');
                return $resource('api/studyUpload/retrievePublicationByPubMedId/:pubMedId', {
                    'query': { method: 'POST', isArray: false},
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

