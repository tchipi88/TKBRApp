(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseDialogController', CaisseDialogController);

    CaisseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Caisse','Employe'];

    function CaisseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Caisse ,Employe) {
        var vm = this;

        vm.caisse = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.employes = Employe.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.caisse.id !== null) {
                Caisse.update(vm.caisse, onSaveSuccess, onSaveError);
            } else {
                Caisse.save(vm.caisse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:caisseUpdate', result);
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
