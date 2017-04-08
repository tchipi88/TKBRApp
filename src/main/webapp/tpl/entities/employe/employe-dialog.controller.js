(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('EmployeDialogController', EmployeDialogController);

    EmployeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Employe', 'EmployeFonction'];

    function EmployeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Employe, EmployeFonction) {
        var vm = this;

        vm.employe = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.employefonctions = EmployeFonction.query();

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
