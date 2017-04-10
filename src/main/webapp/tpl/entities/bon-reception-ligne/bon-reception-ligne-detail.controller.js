(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonReceptionLigneDetailController', BonReceptionLigneDetailController);

    BonReceptionLigneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'BonReceptionLigne' ,'BonReception','CommandeLigne'];

    function BonReceptionLigneDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, BonReceptionLigne ,BonReception,CommandeLigne) {
        var vm = this;

        vm.bonReceptionLigne = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:bonReceptionLigneUpdate', function(event, result) {
            vm.bonReceptionLigne = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
