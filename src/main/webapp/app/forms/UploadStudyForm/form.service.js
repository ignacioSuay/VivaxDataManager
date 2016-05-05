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
    });
