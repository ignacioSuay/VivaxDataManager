(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .factory('StudySearch', StudySearch);

    StudySearch.$inject = ['$resource'];

    function StudySearch($resource) {
        var resourceUrl =  'api/_search/studies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
