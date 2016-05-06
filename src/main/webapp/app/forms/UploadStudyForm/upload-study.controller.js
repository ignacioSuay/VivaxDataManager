(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('UploadStudyController', UploadStudyController);

    UploadStudyController.$inject = ['$scope', '$state', '$http', 'Form'];
    console.log('In publication controller');

    function UploadStudyController ($scope, $state, $http, Form) {

        $scope.publi;
        $scope.pubMedId;

        $scope.retrievePublicationByPubMedId = function () {

            Form.load($scope.pubMedId).then(function (result) {
                $scope.publi = result;
                console.log($scope.publi);
            });
        };
    }

})();
