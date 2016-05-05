(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('UploadStudyController', UploadStudyController);

    UploadStudyController.$inject = ['$scope', '$state', 'PublicationService'];
    console.log('In publication controller');

    function UploadStudyController ($scope, $state, PublicationService) {

        $scope.publi;
        $scope.pubMedId;

        $scope.retrievePublicationByPubMedid = function () {
            PublicationService.query($scope.pubMedId, function (result) {
                console.log('In method');
                $scope.publi = result;
                console.log($console.publi);
            });
        };

    }

})();
