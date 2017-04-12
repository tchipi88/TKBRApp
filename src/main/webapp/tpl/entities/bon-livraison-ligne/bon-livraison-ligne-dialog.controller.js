(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonLivraisonLigneDialogController', BonLivraisonLigneDialogController);

    BonLivraisonLigneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'BonLivraisonLigne','BonLivraison','CommandeLigne'];

    function BonLivraisonLigneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, BonLivraisonLigne ,BonLivraison,CommandeLigne) {
        var vm = this;

        vm.bonLivraisonLigne = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.bonlivraisons = BonLivraison.query();
vm.commandelignes = CommandeLigne.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bonLivraisonLigne.id !== null) {
                BonLivraisonLigne.update(vm.bonLivraisonLigne, onSaveSuccess, onSaveError);
            } else {
                BonLivraisonLigne.save(vm.bonLivraisonLigne, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:bonLivraisonLigneUpdate', result);
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
