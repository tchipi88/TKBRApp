(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ProduitFournisseurDetailController', ProduitFournisseurDetailController);

    ProduitFournisseurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProduitFournisseur', 'Fournisseur', 'Produit'];

    function ProduitFournisseurDetailController($scope, $rootScope, $stateParams, previousState, entity, ProduitFournisseur, Fournisseur, Produit) {
        var vm = this;

        vm.produitFournisseur = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tkbrApp:produitFournisseurUpdate', function(event, result) {
            vm.produitFournisseur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
