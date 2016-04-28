(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('UploadStudyController', UploadStudyController);

    UploadStudyController.$inject = ['$scope'];
    function UploadStudyController ($scope, $state) {
        alert("ueee");
    }

})();
