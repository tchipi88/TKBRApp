(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonReceptionLigneDialogController', BonReceptionLigneDialogController);

    BonReceptionLigneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'BonReceptionLigne','BonReception','CommandeLigne'];

    function BonReceptionLigneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, BonReceptionLigne ,BonReception,CommandeLigne) {
        var vm = this;

        vm.bonReceptionLigne = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.bonReceptions = BonReception.query();
vm.commandeLignes = CommandeLigne.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bonReceptionLigne.id !== null) {
                BonReceptionLigne.update(vm.bonReceptionLigne, onSaveSuccess, onSaveError);
            } else {
                BonReceptionLigne.save(vm.bonReceptionLigne, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:bonReceptionLigneUpdate', result);
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
