(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProduitFournisseurDialogController', ProduitFournisseurDialogController);

    ProduitFournisseurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ProduitFournisseur','Fournisseur','Produit'];

    function ProduitFournisseurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ProduitFournisseur ,Fournisseur,Produit) {
        var vm = this;

        vm.produitFournisseur = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
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


        
        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
