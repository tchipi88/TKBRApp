(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('FournisseurDetailController', FournisseurDetailController);

    FournisseurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Fournisseur', 'ProduitFournisseur'];

    function FournisseurDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Fournisseur, ProduitFournisseur) {
        var vm = this;

        vm.fournisseur = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:fournisseurUpdate', function(event, result) {
            vm.fournisseur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
