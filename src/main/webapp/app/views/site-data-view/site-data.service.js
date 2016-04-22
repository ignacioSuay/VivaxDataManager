(function() {
    'use strict';
    angular
        .module('vivaxDataManagerApp')
        .factory('SiteDataViewDTO', SiteDataViewDTO);

    SiteDataViewDTO.$inject = ['$resource'];

    function SiteDataViewDTO ($resource) {
    	var resourceUrl =  'api/siteDataView/:id';

        return $resource(resourceUrl, {}, {
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
})();
