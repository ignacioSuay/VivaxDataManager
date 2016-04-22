(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .factory('TreatmentSearch', TreatmentSearch);

    TreatmentSearch.$inject = ['$resource'];

    function TreatmentSearch($resource) {
        var resourceUrl =  'api/_search/treatments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
