(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonLivraisonDialogController', BonLivraisonDialogController);

    BonLivraisonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'BonLivraison','Vente','Employe'];

    function BonLivraisonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, BonLivraison ,Vente,Employe) {
        var vm = this;

        vm.bonLivraison = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.ventes = Vente.query();
vm.employes = Employe.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bonLivraison.id !== null) {
                BonLivraison.update(vm.bonLivraison, onSaveSuccess, onSaveError);
            } else {
                BonLivraison.save(vm.bonLivraison, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:bonLivraisonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateLivraison = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
