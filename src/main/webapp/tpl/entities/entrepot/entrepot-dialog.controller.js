(function() {
    'use strict';

    angular
        .module('app')
        .controller('EntrepotDialogController', EntrepotDialogController);

    EntrepotDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Entrepot','Employe'];

    function EntrepotDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Entrepot ,Employe) {
        var vm = this;

        vm.entrepot = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.responsables = Employe.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entrepot.id !== null) {
                Entrepot.update(vm.entrepot, onSaveSuccess, onSaveError);
            } else {
                Entrepot.save(vm.entrepot, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:entrepotUpdate', result);
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
