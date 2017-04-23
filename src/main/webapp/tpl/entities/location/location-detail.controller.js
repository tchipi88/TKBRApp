(function() {
    'use strict';

    angular
        .module('app')
        .controller('LocationDetailController', LocationDetailController);

    LocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Location' ,'Local','Client'];

    function LocationDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Location ,Local,Client) {
        var vm = this;

        vm.location = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
