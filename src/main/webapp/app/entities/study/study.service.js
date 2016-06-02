(function() {
    'use strict';
    angular
        .module('vivaxDataManagerApp')

        .factory('StudyDTO', function ($http) {
            return {
                save: function(study){
                    return $http.post("api/studies/createStudyDTO/",study);
                },
                getStudyTypes: function(){
                    return $http.get("api/studies/types");
                },
                getAllStudiesNonPaged: function(){
                    return $http.get("api/studies/allNonPaged")
                }
            }
        })
        .factory('Study', Study);

    Study.$inject = ['$resource'];

    function Study ($resource) {
        var resourceUrl = 'api/studies/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    }


})();
