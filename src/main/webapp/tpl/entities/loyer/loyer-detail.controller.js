(function() {
    'use strict';

    angular
        .module('app')
        .controller('LoyerDetailController', LoyerDetailController);

    LoyerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Loyer' ,'Location'];

    function LoyerDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Loyer ,Location) {
        var vm = this;

        vm.loyer = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:loyerUpdate', function(event, result) {
            vm.loyer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
