(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('FournisseurDialogController', FournisseurDialogController);

    FournisseurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Fournisseur', 'ProduitFournisseur'];

    function FournisseurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Fournisseur, ProduitFournisseur) {
        var vm = this;

        vm.fournisseur = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.produitfournisseurs = ProduitFournisseur.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fournisseur.id !== null) {
                Fournisseur.update(vm.fournisseur, onSaveSuccess, onSaveError);
            } else {
                Fournisseur.save(vm.fournisseur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:fournisseurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setLogo = function ($file, fournisseur) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        fournisseur.logo = base64Data;
                        fournisseur.logoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
