(function() {
    'use strict';

    angular
        .module('app')
        .controller('AchatDetailController', AchatDetailController);

    AchatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Achat' ,'Fournisseur'];

    function AchatDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Achat ,Fournisseur) {
        var vm = this;

        vm.achat = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:achatUpdate', function(event, result) {
            vm.achat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
