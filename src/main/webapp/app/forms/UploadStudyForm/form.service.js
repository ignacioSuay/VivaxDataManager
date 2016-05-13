'use strict';
    angular.module('vivaxDataManagerApp')
        .factory('Form', function ($http) {
            return {
                save: function(formDTO){
                    return $http.post("api/studyUpload", formDTO);
                },
                load: function(pubMedId){
                    return $http.get("/api/studyUpload/retrievePublicationByPubMedId/"+pubMedId);
                }
            }
    })
        .factory('UpdateTreatmentList', function($resource) {
                return $resource('api/siteData/updateSiteData', {}, {
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

        );
