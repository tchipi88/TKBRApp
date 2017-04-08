(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ProduitFournisseurDialogController', ProduitFournisseurDialogController);

    ProduitFournisseurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProduitFournisseur', 'Fournisseur', 'Produit'];

    function ProduitFournisseurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProduitFournisseur, Fournisseur, Produit) {
        var vm = this;

        vm.produitFournisseur = entity;
        vm.clear = clear;
        vm.save = save;
        vm.fournisseurs = Fournisseur.query();
        vm.produits = Produit.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.produitFournisseur.id !== null) {
                ProduitFournisseur.update(vm.produitFournisseur, onSaveSuccess, onSaveError);
            } else {
                ProduitFournisseur.save(vm.produitFournisseur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:produitFournisseurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
