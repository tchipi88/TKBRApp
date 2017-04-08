(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ReglementDialogController', ReglementDialogController);

    ReglementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reglement', 'Commande'];

    function ReglementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Reglement, Commande) {
        var vm = this;

        vm.reglement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.commandes = Commande.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reglement.id !== null) {
                Reglement.update(vm.reglement, onSaveSuccess, onSaveError);
            } else {
                Reglement.save(vm.reglement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:reglementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateVersement = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
