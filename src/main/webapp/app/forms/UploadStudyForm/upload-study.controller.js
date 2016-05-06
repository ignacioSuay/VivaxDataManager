(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('UploadStudyController', UploadStudyController);

    UploadStudyController.$inject = ['$scope', '$state', '$http', 'Form', '$uibModal'];
    console.log('In publication controller');

    function UploadStudyController ($scope, $state, $http, Form, $uibModal) {

        $scope.publi;
        $scope.pubMedId;
        $scope.publications = [];

        $scope.retrievePublicationByPubMedId = function () {
            Form.load($scope.pubMedId).then(function (result) {
                $scope.publi = result;
                $scope.myHidingValue=true;
                $scope.studies=$scope.publi;
                console.log('@@@@@@@@@@@@@@@@ this is the '+$scope.studies);
                //console.log($scope.publi);
            });
        };

        $scope.newPublication = function () {
            $scope.myHidingValue=false;
            $uibModal.open({
                templateUrl: 'app/entities/publication/publication-dialog.html',
                controller: 'PublicationDialogController',
                size: 'lg',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: function () {
                        return {};
                    }
                }
            }).result.then(function (result) {
                // $scope.publications.push(result);
                console.log('@@@@@@@@@@@@@@@@ this is the ');
                $scope.publi = result;
                $scope.studies=publi.studies;
                console.log('@@@@@@@@@@@@@@@@ this is the '+$scope.studies);
            }, function () {
            })
        };
    }

})();
