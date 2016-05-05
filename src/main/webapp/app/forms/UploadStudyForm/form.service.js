/**
 * Created by steven on 03/05/16.
 */
(function() {
    'use strict';

    angular.module('VivaxDataManagerApp')
        .factory('Form', function ($http, data ) {
            return {
                save: function(formDTO){
                    return $http.post("api/studyUpload", formDTO);
                },
                load: function(pubMedId){
                    return $http.post("api/studyUpload/retrievePublicationByPubMedId/", pubMedId);
                }
            }
    })});
