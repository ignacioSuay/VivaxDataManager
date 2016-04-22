'use strict';

describe('Controller Tests', function() {

    describe('Publication Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPublication, MockStudy;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPublication = jasmine.createSpy('MockPublication');
            MockStudy = jasmine.createSpy('MockStudy');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Publication': MockPublication,
                'Study': MockStudy
            };
            createController = function() {
                $injector.get('$controller')("PublicationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vivaxDataManagerApp:publicationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
