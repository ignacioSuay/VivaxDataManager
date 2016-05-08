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
        $scope.studies = [];
        $scope.siteDatas = [];
        $scope.treatments = [];

        $scope.retrievePublicationByPubMedId = function () {
            Form.load($scope.pubMedId).then(function (result) {
                $scope.publi = result;
                $scope.myHidingValue=true;
                $scope.studies=$scope.publi.data.studies;
                $scope.siteDatas=$scope.publi.data.siteDatas;
                $scope.treatments=$scope.publi.data.treatments;
                //console.log($scope.treatments[0][1]/*.treatment[0].id*/);
                //console.log($scope.siteDatas[0][2]);
                //console.log($scope.studies);
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
                $scope.publi = result;
            }, function () {
            })
        };
    }

})();
