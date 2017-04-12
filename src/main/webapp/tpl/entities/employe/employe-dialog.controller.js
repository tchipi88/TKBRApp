(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeDialogController', EmployeDialogController);

    EmployeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Employe','EmployeFonction','EmployeDepartement'];

    function EmployeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Employe ,EmployeFonction,EmployeDepartement) {
        var vm = this;

        vm.employe = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.fonctions = EmployeFonction.query();
vm.departements = EmployeDepartement.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employe.id !== null) {
                Employe.update(vm.employe, onSaveSuccess, onSaveError);
            } else {
                Employe.save(vm.employe, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:employeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateNaissance = false;
 vm.datePickerOpenStatus.dateEntree = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
