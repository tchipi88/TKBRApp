(function() {
    'use strict';

    angular
        .module('app')
        .controller('AccessGroupDetailController', AccessGroupDetailController);

    AccessGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'AccessGroup' ];

    function AccessGroupDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, AccessGroup ) {
        var vm = this;

        vm.accessGroup = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:accessGroupUpdate', function(event, result) {
            vm.accessGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
