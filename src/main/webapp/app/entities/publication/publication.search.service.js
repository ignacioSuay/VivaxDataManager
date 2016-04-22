(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .factory('PublicationSearch', PublicationSearch);

    PublicationSearch.$inject = ['$resource'];

    function PublicationSearch($resource) {
        var resourceUrl =  'api/_search/publications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
