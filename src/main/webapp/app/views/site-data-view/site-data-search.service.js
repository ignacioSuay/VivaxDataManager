(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .factory('SiteDataSearch', SiteDataSearch);
    
    SiteDataSearch.$inject = ['$resource'];

    function SiteDataSearch($resource) {
    	
        var resourceUrl =  'api/_search/siteDataView';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();